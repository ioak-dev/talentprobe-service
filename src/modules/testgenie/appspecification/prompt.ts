import * as Handlebars from "handlebars";
import { cloneDeep } from "lodash";

const _MODEL_NAME_GPT3 = "gpt-3.5-turbo";
const _MODEL_NAME_GPT4 = "gpt-4o";
const _MODEL_NAME = _MODEL_NAME_GPT4;


const _TESTGENIE_APP_PROMPT = {
  model: _MODEL_NAME,
  messages: [
    {
      role: "system",
      content:
        "Overall Objective: Assist the user in generating all possible usecases for the following description of a application. Instruction: Generate as many use cases as possible, covering all scenarios. Each use case should be represented as an object inside the 'useCases' array and each use case should be restricted to 1 particular functionality and should have the following fields are: name: A relevant name for the usecase. description: A detailed description of the usecase describing the functionality as an end user in such a way that test cases could be easily driven out of it either manually or using AI. priority: The priority level of the use case (string). category: Any labels or category associated with the use case (string).",
    },
    {
      role: "user",
      content:
        "Please use the following application specific description to generate the use cases. Application Description: {{description}}",
    },
  ],
  temperature: 1,
  max_tokens: 3071,
  top_p: 1,
  frequency_penalty: 0,
  presence_penalty: 0,
};

export const getAppUseCasesPrompt = (text: string) => {
  const testGenieAppPrompt = cloneDeep(_TESTGENIE_APP_PROMPT);
  testGenieAppPrompt.messages[1].content = Handlebars.compile(
      testGenieAppPrompt.messages[1].content
  )({ description: text, modelName: _MODEL_NAME });
  return testGenieAppPrompt;
};
