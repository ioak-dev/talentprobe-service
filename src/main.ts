if (module.hot) {
  module.hot.accept();
  module.hot.dispose(() => server.stop());
}

const { ApolloServer } = require("apollo-server-express");
import { authorize } from "./middlewares";
import mongoose from "mongoose";
import { initializeSequences } from "./startup";
const express = require("express");
const cors = require("cors");

var ApiRoute = require("./route");

const gqlScalarSchema = require("./modules/gql-scalar");
const assetSchema = require("./modules/asset");
const sessionSchema = require("./modules/session");
const userSchema = require("./modules/user");

const databaseUri = process.env.MONGODB_URI || "mongodb://127.0.0.1:27017";

mongoose.connect(databaseUri, {})
  .then(() => {
    console.log("Connected to MongoDB");
  })
  .catch((error) => {
    console.error("Error connecting to MongoDB:", error);
    process.exit(1); // Optional: Exit process if unable to connect to DB
  });

mongoose.pluralize(undefined);

const app = express();

const server = new ApolloServer({
  typeDefs: [
    gqlScalarSchema.typeDefs,
    assetSchema.typeDefs,
    sessionSchema.typeDefs,
    userSchema.typeDefs,
  ],
  resolvers: [
    gqlScalarSchema.resolvers,
    assetSchema.resolvers,
    sessionSchema.resolvers,
    userSchema.resolvers,
  ],
  context: ({ req, res }: any) => {
    const authString = req.headers.authorization || "";
    const authParts = authString.split(" ");
    let token = "";
    let user = null;
    let asset = "";
    if (authParts.length === 2) {
      token = authParts[1];
      asset = authParts[0];
      user = authorize(token);
    }
    return { user, token, asset };
  },
  introspection: true,
  playground: true,
});

server.start()
  .then(() => {
    server.applyMiddleware({ app });
    // Start the server after Apollo Server is ready
    app.listen({ port: process.env.PORT || 4000 }, () =>
      console.log(
        `🚀 Server ready at http://localhost:${process.env.PORT || 4000}${server.graphqlPath}`
      )
    );
  })
  .catch((error: any) => {
    console.error("Error starting Apollo Server:", error);
    process.exit(1); // Optional: Exit process if unable to start Apollo Server
  });

app.use(cors());

app.get("/hello", (_: any, res: any) => {
  res.send("basic connection to server works. database connection is not validated");
  res.end();
});

app.use(express.json({ limit: '5mb' }));
app.use(express.urlencoded({ extended: true }));

app.use("/api", ApiRoute);

app.use((req: any, res: any) => {
  res.status(404).send("Not found");
});

app.use((err: any, req: any, res: any, next: any) => {
  console.error(err.stack);
  res.status(500).send("Internal Server Error");
});

process.on("uncaughtException", (err) => {
  console.error("Uncaught Exception:", err);
  // Optionally, shut down the server gracefully
  // process.exit(1);
});

process.on("unhandledRejection", (reason, promise) => {
  console.error("Unhandled Rejection at:", promise, "reason:", reason);
  // Optionally, shut down the server gracefully
  // process.exit(1);
});

// Server startup scripts
initializeSequences();
