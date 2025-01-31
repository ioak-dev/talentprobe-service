import * as Handlebars from "handlebars";
import { cloneDeep } from "lodash";

const _MODEL_NAME_GEMINI = "gemini-1.5-flash";
const _MODEL_NAME = _MODEL_NAME_GEMINI;


const _TESTGENIE_PROMPT = {
  model: _MODEL_NAME,
  contents: [
    {
      role: "model",
      parts: [{ text: "Overall Objective: Assist the user in generating regression test cases for the given description of the requirement. Instruction: Generate as many test cases as possible, covering all scenarios, including edge cases and alternative flows. It is crucial to generate a detailed and descriptive test case along with the steps that could be followed to test them. Consider all possible valid and invalid scenarios. Must generate a minimum requested number of test cases completely. If there are more scenarios to cover, generate as many as necessary. Output Format: The response must be a JSON array of test cases adhering to the following schema: [{'description': {'overview': 'string', 'steps': ['string1', 'string2', 'string3'], 'expectedOutcome': 'string'}, 'summary': 'string', 'priority': 'string', 'comments': 'string', 'components': 'string', 'label': 'string'}]. Each test case should be represented as an object inside the array. Key Details: applicationId, requirementId, usecaseId: Unique identifiers related to the application, requirement, and use case. description: Contains three parts: overview: A short introduction or summary of what the test case is about. steps: A step-by-step list to perform the test, including all necessary details. expectedOutcome: The result that should be observed if the test is successful. summary: A concise description of the test case. priority: The importance level of the test case (e.g., High, Medium, Low). comments: Additional remarks or notes about the test case. components: The specific component or module of the application being tested. labels: The label should classify the test case as one of the following, ['positive', 'negative', 'alternative flow', 'edge case']. Ensure to generate test cases covering all scenarios, including valid and invalid inputs, edge cases, and alternative flows. Keep the strings descriptive but avoid using any special characters. Make sure the JSON structure of the response is correctly formatted. Ensure the steps are detailed enough to execute the test cases effectively. Ensure that if you are giving a testcase that includes special characters, then do not include the example of special characters in your response. Just use the word special characters to refer to them."}],
    },
    {
      role: "user",
      parts: [{
        text:
          "Please use the following description to generate minimum 10-15 or more test cases. Description: {{description}}"
      }],
    },
  ],
  generationConfig: {
    maxOutputTokens: 3071,
    temperature: 0.5
  }
};

export const getTestCaseGenPrompt = (data: string) => {
  const testGeniePrompt = cloneDeep(_TESTGENIE_PROMPT);
  testGeniePrompt.contents[1].parts[0].text = Handlebars.compile(
    testGeniePrompt.contents[1].parts[0].text
  )({ description: data, modelName: _MODEL_NAME });
  return testGeniePrompt;
};
