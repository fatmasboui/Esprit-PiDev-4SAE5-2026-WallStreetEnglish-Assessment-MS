import pandas as pd
import xgboost as xgb
import joblib
import os

# Paths
DATASET_PATH = '../Downloads/ia/adaptive_english_learning_dataset_2024_fixed.csv'
MODEL_NAME = 'xgboost_model.pkl'

def train():
    if not os.path.exists(DATASET_PATH):
        # Try local path if not in Downloads
        DATASET_PATH_LOCAL = 'adaptive_english_learning_dataset_2024_fixed.csv'
        if os.path.exists(DATASET_PATH_LOCAL):
            path = DATASET_PATH_LOCAL
        else:
            print(f"Dataset not found at {DATASET_PATH}")
            return
    else:
        path = DATASET_PATH

    print(f"Loading dataset from {path}...")
    df = pd.read_csv(path)

    # Features and Target
    # Mapping based on app.py expectations
    features = [
        'reading_score', 
        'listening_score', 
        'speaking_score', 
        'reading_speed_wpm', 
        'pronunciation_accuracy'
    ]
    target = 'adaptive_score'

    X = df[features]
    y = df[target]

    print("Training XGBoost Regressor...")
    model = xgb.XGBRegressor(
        n_estimators=100,
        max_depth=6,
        learning_rate=0.1,
        random_state=42
    )
    
    model.fit(X, y)

    print(f"Saving model to {MODEL_NAME}...")
    joblib.dump(model, MODEL_NAME)
    print("Done!")

if __name__ == "__main__":
    train()
