from flask import Flask, jsonify, request
from flask_sqlalchemy import SQLAlchemy
from sqlalchemy import create_engine
from sqlalchemy_utils import database_exists, create_database
from datetime import datetime
import pytz

db = SQLAlchemy()

class Task(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    title = db.Column(db.String(100))
    description = db.Column(db.String(200))
    category = db.Column(db.String(50))
    status = db.Column(db.String(20))
    createdTime = db.Column(db.String(30))
    finishedTime = db.Column(db.String(30))
    duration = db.Column(db.String(100))

    def __repr__(self):
        return f'<Task {self.title}>'

def create_app():
    
    app = Flask(__name__)
    database = "taskapp"

    # if using docker
    app.config["SQLALCHEMY_DATABASE_URI"] = f"mysql://root:root@db/{database}"
    app.config["SQLALCHEMY_TRACK_MODIFICATIONS"] = False
    
    
    engine = create_engine(app.config["SQLALCHEMY_DATABASE_URI"])
    
    if not database_exists(engine.url):
        create_database(engine.url)
        print(f"Database '{database}' created.")
    
    db.init_app(app)
    
    with app.app_context():
        db.create_all()
        
    return app
    
    
app = create_app()
    
@app.route('/')
def first_page():
    print("API for task app")
    return "API for task app"
    
@app.route('/api/add-new-task', methods=['POST'])
def add_new_task():
    try:
        
        data = request.json
        title = data.get('title')
        description = data.get('description')
        status = data.get('status')
        category = data.get('category')
        createdTime = data.get('createdTime')
        
        new_task = Task(
            title = title,
            description = description,
            category = category,
            status = status,
            createdTime = createdTime
        )
        
        db.session.add(new_task)
        db.session.commit()
        
        return jsonify({'message': 'New Task Created Successfully', 'id': new_task.id})
    except Exception as e:
        return jsonify({'error': str(e)}), 500
    
@app.route('/api/get-all-task', methods=['GET'])
def get_all_task():
    try:
        all_task = Task.query.all()
        
        all_task_list = []
        
        for item in all_task:
            all_task_list.append({
                'id': item.id,
                'title': item.title,
                'description': item.description,
                'status': item.status,
                'category': item.category,
                'createdTime': item.createdTime,
                'finishedTime': item.finishedTime,
                'duration': item.duration
            })
        return jsonify({'task': all_task_list})
    except Exception as e:
        return jsonify({'error': str(e)}), 500 
                
@app.route('/api/edit-task/<int:id>', methods=['PUT'])
def edit_task_status(id):
    try:
        task = Task.query.get(id)
        if not task:
            return jsonify({'message': 'Task not found!'}), 404

        new_status = request.json.get('status')
        if new_status is None:
            return jsonify({'message': 'Status field is required!'}), 400


        task.status = new_status
        if new_status == "2":  # Status "Done"
            utc_plus_7 = pytz.timezone('Asia/Bangkok')
            now_utc = datetime.now(pytz.utc)
            now_utc_plus_7 = now_utc.astimezone(utc_plus_7)
            task.finishedTime = now_utc_plus_7.strftime('%Y-%m-%d %H:%M:%S')

            finished_time = datetime.strptime(task.finishedTime, '%Y-%m-%d %H:%M:%S')
            created_time = datetime.strptime(task.createdTime, '%Y-%m-%d %H:%M:%S')

            duration_seconds = (finished_time - created_time).total_seconds()
            days = int(duration_seconds // (24 * 3600))
            duration_seconds %= (24 * 3600)
            hours = int(duration_seconds // 3600)
            duration_seconds %= 3600
            minutes = int(duration_seconds // 60)
            seconds = int(duration_seconds % 60)

            formatted_duration = f"{days:02}:{hours:02}:{minutes:02}:{seconds:02}"

            task.duration = str(formatted_duration)

        db.session.commit()

        return jsonify({'message': 'Task status updated successfully!'})
    except Exception as e:
        return jsonify({'error': str(e)}), 500


if __name__ == '__main__':
    app.run(debug=True)