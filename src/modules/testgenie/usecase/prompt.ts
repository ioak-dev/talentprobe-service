import * as Handlebars from "handlebars";
import { cloneDeep } from "lodash";

const _MODEL_NAME_GEMINI = "gemini-1.5-flash";
const _MODEL_NAME = _MODEL_NAME_GEMINI;


const _TESTGENIE_PROMPT = {
  model: _MODEL_NAME,
  contents: [
    {
      role: "model",
      parts: [{ text: "Overall Objective: Assist the user in generating regression use cases for the given description of the requirement. Instruction: Generate as many use cases as possible, covering all scenarios, including edge cases and alternative flows. It is crucial to generate a detailed and descriptive usecase. Consider all possible valid and invalid scenarios. Must generate a minimum requested number of use cases completely. If there are more scenarios to cover, generate as many as necessary. OutputFormat: The response must be a JSON array of test cases with the following structure:jsonCopy[{'description': 'string'}], here description should contain the detailed explanation of the usecase. Do not include any headers with the description, just provide the detailed description in plain text. Each use case should be represented as an object inside the 'useCases' array."}],
    },
    {
      role: "user",
      parts: [{
        text:
          "Please use the following description to generate minimum 10-15 or more use cases. Description: {{description}}"
      }],
    },
  ],
  generationConfig: {
    maxOutputTokens: 3071,
    temperature: 0.5
  }
};

export const getUseCaseGenPrompt = (data: string) => {
  const testGeniePrompt = cloneDeep(_TESTGENIE_PROMPT);
  testGeniePrompt.contents[1].parts[0].text = Handlebars.compile(
    testGeniePrompt.contents[1].parts[0].text
  )({ description: data, modelName: _MODEL_NAME });
  return testGeniePrompt;
};
