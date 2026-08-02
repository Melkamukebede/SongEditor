# 🎵 Song Editor App

A simple Android app that allows users to manage a list of songs stored in a JSON file.  
You can **add**, **update**, and **view** songs with ease.

## 📱 Features

- 📋 Display songs in a RecyclerView
- ✏️ Update existing song details
- ➕ Add new songs (prevents duplicates)
- 💾 Persistent storage using internal storage (JSON)
- 🔄 Auto-load from `assets/songs.json` on first launch
- 🎯 Click on any song to populate edit fields

## 🛠️ Tech Stack

- **Language:** Java
- **UI:** XML + Material Design
- **Storage:** JSON file in internal storage
- **JSON Parsing:** org.json (Android built-in)
- **RecyclerView:** For listing songs
- **Android Studio:** Latest stable version

## 📂 Project Structure

See the [file structure](#) section in the repo.

## 🚀 Getting Started

### Prerequisites

- Android Studio Arctic Fox or newer
- Android SDK (API 21+)
- Git

### Clone & Build

```bash
git clone https://github.com/yourusername/SongEditorApp.git
cd SongEditorApp
