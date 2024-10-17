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
        "Overall Objective: Assist the user in generating a set of detailed regression test cases for the given consolidated application use cases. Instruction: Generate as many test cases as possible, covering all scenarios, with responses in JSON format. Generate a minimum of 12-15 test cases, but you can generate more if necessary to cover all scenarios. Each test case should be represented as an object inside the 'testCases' array. Each test case should have the following fields: description: This should be a JSON object containing 'overview', 'steps', and 'expectedOutcome' fields: overview: A string describing the purpose of the test case in detail. steps: A list of strings detailing the steps to execute the test case. Each step should start with a number followed by a period. expectedOutcome: A string explaining the expected outcome of the test case. summary: A detailed summary of the test case (string). priority: The priority level of the test case (string). components: The components covered by the test case (string). comments: Any additional comments related to the test case (string). labels: Any labels associated with the test case (string). Ensure each test case is comprehensive and covers different scenarios within the provided use cases.",
    },
    {
      role: "user",
      content:
          "Please use the following consolidated application use cases to generate the test cases: {{usecases}}",
    },
  ],
  temperature: 1,
  max_tokens: 4096,
  top_p: 1,
  frequency_penalty: 0,
  presence_penalty: 0,
  stop: "None",
  n: 1,
};

export const getTestGenieApplicationPrompt = (text: string) => {
  const testGenieAppPrompt = cloneDeep(_TESTGENIE_APP_PROMPT);
  testGenieAppPrompt.messages[1].content = Handlebars.compile(
      testGenieAppPrompt.messages[1].content
  )({ usecases: text, modelName: _MODEL_NAME });
  return testGenieAppPrompt;
};
