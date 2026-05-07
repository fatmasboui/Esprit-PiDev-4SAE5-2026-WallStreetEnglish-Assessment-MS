from flask import Flask, request, jsonify
import joblib
import numpy as np
import xgboost as xgb
import os
import pandas as pd

app = Flask(__name__)
MODEL_PATH = 'xgboost_model.pkl'
DATASET_PATH = 'adaptive_english_learning_dataset_2024_fixed.csv'

def check_and_train_model():
    if not os.path.exists(MODEL_PATH):
        if os.path.exists(DATASET_PATH):
            print("Model missing, but dataset found. Training now...")
            try:
                df = pd.read_csv(DATASET_PATH)
                features = ['reading_score', 'listening_score', 'speaking_score', 'reading_speed_wpm', 'pronunciation_accuracy']
                target = 'adaptive_score'
                X = df[features]
                y = df[target]
                model = xgb.XGBRegressor(n_estimators=100, max_depth=6, learning_rate=0.1, random_state=42)
                model.fit(X, y)
                joblib.dump(model, MODEL_PATH)
                print("Training complete!")
                return True
            except Exception as e:
                print(f"Error during training: {e}")
                return False
        else:
            print("Model and dataset missing. Using default responses.")
            return False
    return True

@app.route('/')
def home():
    return jsonify({
        "status": "AI Service is running",
        "endpoints": {
            "predict": "/predict [POST]"
        }
    })

@app.route('/predict', methods=['POST'])
def predict():
    has_model = check_and_train_model()
    
    if not has_model:
        return jsonify({"recommended_adaptive_score": 50.0, "status": "default"})
    
    try:
        data = request.get_json()
        model = joblib.load(MODEL_PATH)
        
        # features order: reading_score, listening_score, speaking_score, reading_speed_wpm, pronunciation_accuracy
        features = np.array([[
            data.get('reading', 50),
            data.get('listening', 50),
            data.get('speaking', 50),
            data.get('speed', 100),
            data.get('accuracy', 70)
        ]])
        
        prediction = model.predict(features)
        return jsonify({"recommended_adaptive_score": float(prediction[0])})
    except Exception as e:
        return jsonify({"error": str(e), "recommended_adaptive_score": 50.0})

if __name__ == '__main__':
    check_and_train_model()
    app.run(host='0.0.0.0', port=5000)
