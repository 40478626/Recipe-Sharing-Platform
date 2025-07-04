import csv
import random
import uuid
from datetime import datetime
from faker import Faker
from pathlib import Path

fake = Faker()

# Output folder
output_dir = Path("recipe_sharing_csvs")
output_dir.mkdir(parents=True, exist_ok=True)

def random_date():
    return fake.date_time_this_decade().isoformat()

def save_csv(data, filename):
    if data:
        fieldnames = data[0].keys()  # Extract fieldnames from the first row
    else:
        return

    with open(output_dir / filename, "w", newline="", encoding="utf-8") as f:
        writer = csv.DictWriter(f, fieldnames=fieldnames)
        writer.writeheader()
        writer.writerows(data)

# 1. Category table
category_data = [
    {
        "id": i + 1,
        "created_at": "2025-03-23T15:50:57",
        "name": f"Category {fake.word().capitalize()}",
        "updated_at": "2025-03-23T15:50:57"
    }
    for i in range(130)
]
save_csv(category_data, "category.csv")

# 2. Collection table
collection_data = [
    {
        "id": i + 1,
        "created_at": random_date(),
        "name": f"Collection {fake.word().capitalize()}",
        "recipe_id": random.randint(1, 130),
        "update_at": random_date(),
        "user_id": random.randint(1, 30)
    }
    for i in range(130)
]
save_csv(collection_data, "collection.csv")

# 3. Comment table
comment_data = [
    {
        "id": i + 1,
        "comment": fake.sentence(),
        "created_at": random_date(),
        "name": fake.name(),
        "recipe_id": random.randint(1, 130),
        "update_at": random_date(),
        "user_id": random.randint(1, 30)
    }
    for i in range(130)
]
save_csv(comment_data, "comment.csv")

# 4. Dietary Preferences table (realistic data and linked to Recipe)
dietary_choices = ["Vegan", "Vegetarian", "Keto", "Halal", "Gluten-Free", "Paleo", "Low-Carb"]

dietary_data = [
    {
        "id": i + 1,
        "created_at": random_date(),
        "name": random.choice(dietary_choices),
        "update_at": random_date(),
    }
    for i in range(130)
]
save_csv(dietary_data, "dietary_preferences.csv")

# 5. Rate table
rate_data = [
    {
        "id": i + 1,
        "created_at": random_date(),
        "rate": random.randint(1, 5),
        "name": fake.first_name(),
        "recipe_id": random.randint(1, 130),
        "update_at": random_date(),
        "user_id": random.randint(1, 30)
    }
    for i in range(130)
]
save_csv(rate_data, "rate.csv")

# 6. Recipe table (linked to Category and Dietary Preferences)
recipe_data = [
    {
        "id": i + 1,
        "category_name": f"Category {random.choice([x['name'] for x in category_data])}",  # Link to category_name
        "cooking_time": f"{random.randint(10, 90)} mins",
        "description": fake.text(max_nb_chars=100),
        "created_at": random_date(),
        "dietary_preferences": random.choice(dietary_choices),  # Link dietary preference
        "image_id": "",
        "ingredients": ", ".join(fake.words(nb=5)),
        "name": f"{fake.word().capitalize()} Dish",
        "recipe_id": str(uuid.uuid4()),  # Ensure unique recipe ID
        "update_at": random_date(),
        "user_id": ""
    }
    for i in range(130)
]
save_csv(recipe_data, "recipe.csv")

print("✅ CSV files generated in folder: recipe_sharing_csvs")
