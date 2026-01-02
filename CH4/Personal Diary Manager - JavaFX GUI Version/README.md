# Personal Diary Manager - JavaFX GUI Version

A modern, feature-rich personal diary application built with JavaFX and web technologies. This application combines the power of Java for backend operations with the flexibility of HTML/CSS for a beautiful, responsive user interface.

## 🌟 Features

### 🎨 Modern User Interface
- **Hybrid Architecture**: Seamless integration of JavaFX and WebView for a modern look and feel.
- **Responsive Design**: Beautiful dashboard with a sidebar navigation, entry list, and main content area.
- **Theme Support**: Built-in Light and Dark modes to suit your preference.
- **Dynamic Updates**: Real-time date and time display.

### 📝 Rich Text Editing
- **Advanced Editor**: Write your thoughts with a rich text editor supporting formatting (bold, italic, etc.).
- **Auto-Save**: Never lose your work with automatic saving functionality.
- **Distraction-Free Writing**: Dedicated writing mode that overlays the dashboard.

### 📅 Organization & Navigation
- **Calendar View**: Interactive calendar to browse entries by date.
- **Sidebar Navigation**: Quick access to "Today", "Calendar", and "Settings".
- **Entry Browser**: Visual list of recent entries with previews.

### ⚙️ Customization
- **Settings Panel**: Toggle themes and manage notification preferences.
- **Profile Integration**: Personalized greeting and profile section.

## 🛠 Technical Stack

- **Language**: Java 21+
- **GUI Framework**: JavaFX 21 (Controls, FXML, Web)
- **Build Tool**: Maven
- **Frontend**: HTML5, CSS3 (with CSS Variables for theming), JavaScript
- **Dependencies**:
  - `javafx-controls`
  - `javafx-fxml`
  - `javafx-web`
  - `controlsfx`
  - `bootstrapfx-core`

## 🚀 Getting Started

### Prerequisites
- Java Development Kit (JDK) 21 or higher.
- Maven installed on your system.
- An IDE like IntelliJ IDEA (recommended) or Eclipse.

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/personal-diary-manager.git
   cd personal-diary-manager
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn javafx:run
   ```

## 📖 Usage Guide

1. **Dashboard**: Upon launching, you'll see the main dashboard showing today's date and recent entries.
2. **Writing a New Entry**: Click the floating **"+"** button in the bottom right corner to open the rich text editor.
3. **Saving**: Click "Save Entry" to save your work. The editor supports auto-save every 5 seconds.
4. **Browsing**:
   - Click on any entry in the sidebar or main feed to read it.
   - Use the **Calendar** tab to navigate to past dates.
5. **Settings**: Go to the **Settings** tab to switch between Light and Dark modes or toggle notifications.

## 📂 Project Structure

```
src/main/
├── java/org/example/personaldiarymanagergui/
│   ├── DiaryApplication.java    # Main entry point
│   ├── DiaryController.java     # Controller for UI logic and Java-JS bridge
│   └── Launcher.java            # Application launcher
└── resources/org/example/personaldiarymanagergui/
    ├── dashboard-view.fxml      # Main JavaFX layout
    ├── DiaryUI/                 # Web resources
    │   ├── index.html           # Main UI structure
    │   └── style.css            # Styling and themes
    ├── light-theme.css          # JavaFX Light Theme
    └── dark-theme.css           # JavaFX Dark Theme
```

*Developed by Addishiwot abdisa*
