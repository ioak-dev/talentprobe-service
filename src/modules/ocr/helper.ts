const Tesseract = require("tesseract.js");

export const parseImage = async (imageBuffer: any) => {
  const {
    data: { text },
  } = await Tesseract.recognize(imageBuffer, "eng", {
    logger: (m: any) => console.log(m),
  });

  console.log(text);

  const lineItems = parseText(text);
  return { text, lineItems };
};

const parseText = (text: string) => {
  const lines = text.split("\n");
  const items: any[] = [];
  let currentItem: any = {};
  let captureDescription = false;

  lines.forEach((line) => {
    line = line.trim();

    // Regular expression to capture item_no, quantity_ordered, um, and start of description
    const itemMatch = line.match(/^(\d+)\s+(\w+)\s+(.+)$/);
    if (itemMatch) {
      const [_, quantity, um, description] = itemMatch;
      currentItem = {
        quantity_ordered: parseInt(quantity),
        um: um.trim(),
        description: description.trim(),
      };
      captureDescription = true;
    } else if (captureDescription) {
      // Continue capturing description until we find the PR line
      const prMatch = line.match(/^PR\s+(\d+)\s+Line:\s+(\d+)/);
      if (prMatch) {
        currentItem.description = currentItem.description + " " + line.trim();
        currentItem.pr_number = prMatch[1].trim();
        currentItem.line_number = prMatch[2].trim();
        captureDescription = false;
      } else {
        currentItem.description = currentItem.description + " " + line.trim();
      }
    } else if (line.match(/^Approved By:/)) {
      // Matches the line with approval information
      const match = line.match(/^Approved By:\s+(.+)$/);
      if (match) {
        currentItem.approved_by = match[1].trim();
        items.push(currentItem);
        currentItem = {}; // Reset for next item
      }
    }
  });

  return items;
};
