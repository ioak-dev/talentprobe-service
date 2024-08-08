const express = require("express");
const router = express.Router();

router.get("/", (_: any, res: any) => {
  res.send("v1.0.0");
  res.end();
});

require("./modules/hello/route")(router);
require("./modules/auth/route")(router);
require("./modules/user/route")(router);
require("./modules/user/invite/route")(router);
require("./modules/company/route")(router);
require("./modules/budget/route")(router);
require("./modules/assessment/route")(router);
require("./modules/assessment/question/route")(router);
require("./modules/assessment/responseheader/route")(router);
require("./modules/assessment/responsedetail/route")(router);
require("./modules/suite/route")(router)
require("./modules/testcase/route")(router)
require("./modules/usecase/route")(router)

module.exports = router;
