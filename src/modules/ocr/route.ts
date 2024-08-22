import { asyncHandler } from "../../handler";
import { parseImage } from "./service";

const multer = require("multer");
const Tesseract = require("tesseract.js");
const upload = multer({ storage: multer.memoryStorage() });

module.exports = function (router: any) {
  router.post("/ocr", upload.single("file"), asyncHandler(parseImage));
};
