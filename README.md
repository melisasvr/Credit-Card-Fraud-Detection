# 💳 Credit Card Fraud Detection (Pure Java)
- A lightweight, zero-dependency machine learning system designed to detect fraudulent credit card transactions. 
- This project uses Logistic Regression and Stochastic Gradient Descent, written in native Java, to classify transactions as Safe or High Risk.
## 🛠 Technical Architecture
- The core engine is built on a Logistic Regression model.
- Unlike deep learning approaches, which require heavy GPU/CPU native libraries, this model focuses on feature weighting and probability mapping to ensure sub-millisecond classification.

## 🚀 Key Features
- Zero-Dependency Build: Compiled using standard JDK 17+; no Maven or Gradle required.
- Real-Time Inference: Includes an interactive command-line interface for manual transaction auditing.
- Automated Feature Scaling: Implements Z-score normalization logic for the Amount and Time domains to prevent gradient explosion.
- Metric Suite: Reports Accuracy, Fraud Recall, and False Alarm rates (Precision) out-of-the-box.

## 📂 Project Structure
- DataGenerator.java: A utility to create a synthetic creditcard.csv with 500+ rows of patterned data.
- SimpleFraudDetector.java: The "Brain" of the project. Handles data loading, Z-score scaling, model training, and real-time prediction.
- creditcard.csv: The dataset containing 30 features (Time, V1-V28, Amount) and the Class (0 for Normal, 1 for Fraud).

## 📂 Implementation Details
 - Data Pipeline
- The system expects a creditcard.csv structure mirroring the standard Kaggle distribution:
- Features 1-28: PCA-transformed anonymized variables.
- Feature 29: Transaction Amount (auto-scaled).
- Target: Binary Class (0: Normal, 1: Fraud).

## 💻 Getting Started
- Prerequisites
- Java Development Kit (JDK) 17 or higher.
- Installation & Execution
1. Dataset Generation: Generate the synthetic patterned dataset for training:
- `javac DataGenerator.java && java DataGenerator`
2. Model Training & Inference Compile and launch the detector:
- `javac SimpleFraudDetector.java && java SimpleFraudDetector`

## ⚖️ License
- Distributed under the MIT License. See LICENSE for more information.
```
MIT License

Copyright (c) 2026
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including, without limitation, the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES, OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT, OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
## 🤝 Contributing
- Contributions welcome!
