# 🌌 Galaxy Scanner - Celestial Object Detection and Analysis
Galaxy Scanner is a Java-based desktop application designed to process and analyze astronomical images. It identifies celestial objects ("stars") within an image, 
colors them based on size and other criteria, and provides detailed statistics and visualizations to help users explore the characteristics of each detected object.

## ✨ Features
- Star Detection & Segmentation: Automatically segments stars in an astronomical image based on a user-defined minimum size threshold.
- Color Coding Stars: Highlights detected stars with different random colors to visually distinguish objects.
- Single Star Highlighting: Allows coloring of a specific star by its index.
- Sorting & Size Analysis: Sorts stars by size using a quicksort implementation for efficient ranking.
- Detailed Data Display: Generates a tree view showing detailed attributes of stars, including estimated elemental composition (Sulphur, Hydrogen, Oxygen) and pixel size.
- Histogram Visualization: Displays color histograms for the loaded image.
- Interactive UI: Controls for image loading, threshold adjustment, and star selection.

## ⚙️ Installation and Setup
**Prerequisites:**
- Java Development Kit (JDK) 8 or higher.
- JavaFX SDK compatible with your JDK version.
- IDE such as IntelliJ IDEA, Eclipse, or NetBeans for easier development and debugging.
- 

**Clone the Repository:**
- ```git clone https://github.com/yourusername/galaxy-scanner.git```
- ```cd galaxy-scanner```
  
**Build and Run:**
- Import the project into your Java IDE.
- Make sure JavaFX libraries are correctly linked.
- Run the ScannerController class as the main application.

## 🚀 Usage
**Loading and Displaying Images**
- The application allows users to load an astronomical image (currently hardcoded to a path, can be modified for file chooser).
- The loaded image is displayed on the main panel.

**Star Detection and Processing**
- Set the minimum star size in pixels through the provided input.
- Adjust the threshold slider to control image segmentation sensitivity.
- Click buttons to process the image:
  - Black and White: Segments stars based on the threshold and displays results.
  - Single Star Change: Highlights one star by its index with a random color.
  - Multi Change: Colors all stars that meet size criteria randomly.
  - Highlight: Highlights detected stars.

**Viewing Star Details**
- The tree view displays all detected stars with:
  - Estimated elemental averages (Sulphur, Hydrogen, Oxygen) based on color channels.
  - Estimated pixel size.
- Expand or collapse the tree for detailed or summarized views.
- Search and display details for a single star by entering its number.

**Histogram Visualization**
- Displays color intensity histograms for the loaded image.
- Updates with the current image displayed.

## 📂 Code Structure Overview
- ScannerController: JavaFX controller managing UI interactions and calling core processing methods.
- Merging (not fully shown): Core image processing including segmentation, star detection, coloring, and data aggregation.
- Sorting: Implements quicksort for sorting stars by size in descending order.
- unionFind: Implements union-find data structure for grouping connected pixels into stars.
- DrawHistogram (assumed): Handles histogram generation and display.

## 🛠️ Important Classes and Methods
- differentColorStars: Colors all stars above a size threshold with random colors.

- oneStarChange: Colors a specific star by its index.
- sortSpotsBySize: Returns a sorted list of stars by size using quicksort.
- createDetailTree: Generates a TreeView hierarchy with star data.
- printSingleStar: Displays detailed info for one star in the TreeView.
- quickSort (Sorting): Sorts star roots and sizes in descending order.
- find & union (unionFind): Basic union-find operations to detect connected pixel sets.

## 🔮 Future Improvements
- Add dynamic file chooser support instead of hardcoded file paths.
- Improve UI responsiveness during image processing.
- Add saving and exporting functionality for processed images and star data.
- Include more detailed spectral analysis based on pixel colors.
- Support larger images and optimize performance.
