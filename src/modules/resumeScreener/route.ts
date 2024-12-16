import multer from 'multer';
import { uploadResume } from "./service";
import { authorizeApi } from '../../middlewares';
import path from 'path';

const storage = multer.diskStorage({
  destination: (req, file, cb) => {
    cb(null, 'uploads/'); 
  },
  filename: (req, file, cb) => {
    const uniqueSuffix = Date.now() + '-' + Math.round(Math.random() * 1e9);
    cb(null, file.fieldname + '-' + uniqueSuffix + path.extname(file.originalname)); 
  }
});

const upload = multer({ storage });


module.exports =  function(router: any){
  router.post("/resumeScreener/uploadResume", upload.single("file"), authorizeApi, uploadResume);
};