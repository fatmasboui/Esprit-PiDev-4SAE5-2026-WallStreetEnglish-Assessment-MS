import pandas as pd
import numpy as np
import xgboost as xgb
from sklearn.ensemble import RandomForestRegressor
from sklearn.linear_model import LinearRegression
from sklearn.model_selection import train_test_split
from sklearn.metrics import mean_absolute_error, mean_squared_error, r2_score
import joblib
import os

# Paths
DATASET_PATH = 'adaptive_english_learning_dataset_2024_fixed.csv'

def benchmark():
    if not os.path.exists(DATASET_PATH):
        print(f"Error: Dataset {DATASET_PATH} not found.")
        return

    print("--- AI MODEL BENCHMARKING ---")
    df = pd.read_csv(DATASET_PATH)

    features = ['reading_score', 'listening_score', 'speaking_score', 'reading_speed_wpm', 'pronunciation_accuracy']
    target = 'adaptive_score'

    X = df[features]
    y = df[target]

    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

    models = {
        "Linear Regression": LinearRegression(),
        "Random Forest": RandomForestRegressor(n_estimators=100, random_state=42),
        "XGBoost": xgb.XGBRegressor(n_estimators=100, max_depth=6, learning_rate=0.1, random_state=42)
    }

    results = []

    for name, model in models.items():
        print(f"Training {name}...")
        model.fit(X_train, y_train)
        predictions = model.predict(X_test)
        
        mae = mean_absolute_error(y_test, predictions)
        mse = mean_squared_error(y_test, predictions)
        rmse = np.sqrt(mse)
        r2 = r2_score(y_test, predictions)
        
        results.append({
            "Model": name,
            "MAE": round(mae, 4),
            "RMSE": round(rmse, 4),
            "R2 Score": round(r2, 4)
        })

    # Display Results
    results_df = pd.DataFrame(results)
    print("\nBenchmark Results:")
    print(results_df.to_string(index=False))
    
    best_model_name = results_df.iloc[results_df['R2 Score'].idxmax()]['Model']
    print(f"\nBest Model: {best_model_name}")

    # Save results to markdown for the student
    with open('benchmark_results.md', 'w', encoding='utf-8') as f:
        f.write("# Rapport de Benchmarking IA\n\n")
        f.write("Ce rapport compare les performances de différents modèles de Machine Learning pour la prédiction du score adaptatif.\n\n")
        
        # Manual Markdown Table
        f.write("| Model | MAE | RMSE | R2 Score |\n")
        f.write("| :--- | :---: | :---: | :---: |\n")
        for res in results:
            f.write(f"| {res['Model']} | {res['MAE']} | {res['RMSE']} | {res['R2 Score']} |\n")
        
        f.write(f"\n\n**Modele retenu : {best_model_name}** car il presente le meilleur coefficient de determination (R2).")

if __name__ == "__main__":
    benchmark()
