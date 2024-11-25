// // import express, { Request, Response } from "express";
import multer from 'multer';
import { uploadResume } from "./service";

// // const router = express.Router();
const upload = multer({ dest: "uploads/" });

module.exports =  function(router: any){
  router.post("/resumeScreener/uploadResume", upload.single("resume"), uploadResume);
};
