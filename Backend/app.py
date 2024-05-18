from flask import Flask, jsonify, request
from flask_sqlalchemy import SQLAlchemy
from sqlalchemy import create_engine
from sqlalchemy_utils import database_exists, create_database

db = SQLAlchemy()

class Task(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    title = db.Column(db.String(100))
    description = db.Column(db.String(200))
    category = db.Column(db.String(50))
    status = db.Column(db.String(20))
    created_time = db.Column(db.String(30))
    finished_time = db.Column(db.String(30))
    duration = db.Column(db.Integer) 

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
        created_time = data.get('created_time')
        
        new_task = Task(
            title = title,
            description = description,
            category = category,
            status = status,
            created_time = created_time
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
                'created_time': item.created_time,
                'finished_time': item.finished_time,
                'duration': item.duration
            })
        return jsonify({'task': all_task_list})
    except Exception as e:
        return jsonify({'error': str(e)}), 500 
                
@app.route('/api/edit-task/<int:id>', methods=['PUT'])
def edit_task(id):
    try:
        task = Task.query.get(id)
        if not task:
            return jsonify({'message': 'Item not found!'}), 404

        
        task_data = request.json
        fields = ['title', 'description', 'status', 'category']
        for field in fields:
            if field in task_data:
                setattr(task, field, task_data[field])
            
        db.session.commit()
        
        return jsonify({'message': 'Task updated successfully!'})
    except Exception as e:
        return jsonify({'error': str(e)}), 500
    

if __name__ == '__main__':
    app.run(debug=True)