# Excel Export Example with Custom Annotation and Apache POI

This repository is an example of exporting data to Excel using a custom annotation and Apache POI. The Excel export-related code can be found under the `/src/aboutexcel` package.

## Usage

The process of writing data to Excel is handled within a separate class AbstractExcelExporter, while the class that extends it only requires specifying the headers, cell style configurations and the file path.

Annotated fields are transferred to the Excel table. The column is ordered based on the `index`, the header text is written using the `headText`, and the column width is set using `width`. You may need to experiment with different values for column width to achieve the desired result.
The same index cannot be used multiple times.

![Excel View](./resources/images/excel.png)

## Getting Started

To get started with this project, follow the steps below:

1. Clone the repository to your local machine.
2. Open the project in your preferred Java IDE.
3. Navigate to the `src/main/java/com/abtech/aboutexcel` directory.
4. Review the code and explore the example of exporting data to Excel.


![src-aboutexcel](./resources/images/src-aboutexcel.png)

## Requirements

To run this project, make sure you have the following dependencies:

- JDK 14+
- Spring Boot
- Apache POI
- Lombok

![Dependencies](./resources/images/dependencies.png)

## Conclusion

Thank you for viewing this sample project that showcasing data export to Excel using a custom annotation and Apache POI. Feel free to modify and adapt the code according to your needs.

For more details and examples, please refer to the code files in the `/src/../aboutexcel` package.
