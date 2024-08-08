import * as Handlebars from "handlebars";
import { cloneDeep } from "lodash";

const _MODEL_NAME_GPT3 = "gpt-3.5-turbo";
const _MODEL_NAME_GPT4 = "gpt-4o";
const _MODEL_NAME = _MODEL_NAME_GPT4;

export const getTestCaseGenPrompt = (text: string) => {
  const testGenPrompt = cloneDeep(_RESUME_PROMPT);
  testGenPrompt.messages[1].content = Handlebars.compile(
    testGenPrompt.messages[1].content
  )({resume: text, modelName: _MODEL_NAME });
  return testGenPrompt;
};

const _RESUME_PROMPT = {
  model: _MODEL_NAME,
  messages: [
    {
      role: "system",
      content:
        "Overall Objective: Assist the user in generating regression test cases for the given usecase. Instruction: Generate as many test cases as possible, covering all scenarios, with responses in JSON format. It is crucial to generate a complete response in JSON format. Consider all possible valid and invalid scenarios, Include edge cases and alternative flows. Break down each functionality into smaller, more granular test scenarios. Must generate a minimum requested number of test cases completely. If there are more scenarios to cover, generate as many as necessary. Each test case should be represented as an object inside the 'testCases' array. Each test case should have the following fields: 'description': This should be a JSON object containing 'overview', 'steps', and 'expectedOutcome' fields: overview: A string describing the purpose of the test case in detail. steps: A list of strings detailing the proper steps to execute the test case. Each step should start with a number followed by a period. expectedOutcome: A string explaining the expected outcome of the test case. 'summary': A detailed summary of the test case (string). 'priority': The priority level of the test case (string). 'components': The components covered by the test case (string). 'comments': Any additional comments related to the test case (string). 'labels': Any labels associated with the test case (string).",
    },
    {
      role: "user",
      content:
        " Please use the following use case to generate minimum 10-15 or more test cases. Usecase: ${usecase} ",
    },
  ],
  temperature: 1,
  max_tokens: 3071,
  top_p: 1,
  frequency_penalty: 0,
  presence_penalty: 0,
};