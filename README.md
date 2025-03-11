# AiChatBot

## Overview
AiChatBot is a simple AI chatbot designed to answer common questions. It uses a combination of a K-Nearest Neighbors (KNN) classifier and web scraping to provide responses. The chatbot stores interactions in a MySQL database and retrains its model periodically to improve accuracy.

## Features
- **KNN Classifier**: Predicts responses based on past interactions.
- **Web Scraping**: Fetches information from Wikipedia if no match is found in the database.
- **Database Storage**: Stores questions and responses in a MySQL database.
- **Periodic Retraining**: Retrains the KNN model after a set number of interactions.

## Technologies Used
- **Java**: Programming language.
- **JavaFX**: For GUI components.
- **MySQL**: Database for storing questions and responses.
- **Apache OpenNLP**: For natural language processing.
- **JSoup**: For web scraping.
- **Maven**: For project management and dependency management.

## Usage
Interacting with the chatbot: The chatbot can be interacted with through the GUI. Enter a question, and the chatbot will respond based on its training data or fetch information from Wikipedia if no match is found.  
Training the model: The KNN model is retrained automatically after a set number of interactions. You can also manually trigger retraining by modifying the trainModel method in ChatBot.java.
