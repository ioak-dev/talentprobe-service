import multer from 'multer';
import { uploadResume } from "./service";
import { authorizeApi } from '../../middlewares';

const upload = multer({ dest: "uploads/" });

module.exports =  function(router: any){
  router.post("/resumeScreener/uploadResume", upload.single("file"), authorizeApi, uploadResume);
};