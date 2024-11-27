import { AxiosError } from "axios";

const axios = require("axios");
const DODO_URL = process.env.DODO_URL || "https://api.ioak.io:8120";
const DODO_KEY = process.env.DODO_KEY || "a53dc337-a203-4980-bfc8-12f19acddd26 AIzaSyDK79pRO4VvmaYpImCd8rJowlIVy2bC-Cw";

export const predictGemini = async(payload: any)=>{
    try{
        const result = await axios.post(`${DODO_URL}/api/gemini/models/gemini-pro:generateContent`, 
            payload, 
            {headers:
                {
                    'Content-Type': 'application/json',
                    authorization: DODO_KEY, 
                }
            }
        )
        if (result.status === 200) {
            if (
              result.data?.data?.candidates.length > 0 &&
              result.data?.data?.candidates[0]?.content?.parts[0]?.text
            ) {
              const jsonContent = result.data?.data?.candidates[0]?.content?.parts[0].text
                .replace(/```json/g, "")
                .replace(/```/g, "");
              return JSON.parse(jsonContent) || null;
            }
          }
        }catch(err){
        console.log("*", err);
        return "";
    }
};