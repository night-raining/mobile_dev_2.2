from flask import Flask, jsonify, send_file
from flask.templating import render_template
from flask.wrappers import Request
from flask_jwt import JWT, jwt_required, current_identity
from werkzeug.security import safe_str_cmp

from datetime import datetime
import base64


# from OpenSSL import SSL
# context = SSL.Context(SSL.PROTOCOL_TLSv1_2)
# context.use_privatekey_file('key.pem')
# context.use_certificate_file('serverlocalhost.crt')

STATIC_STRING = "Some hardcoded string"

class User(object):
    def __init__(self, id, username, password):
        self.id = id
        self.username = username
        self.password = password

    def __str__(self):
        return "User(id='%s')" % self.id

users = [
    User(1, 'q', 'q'),
    User(2, 'user2', 'abcxyz'),
]

username_table = {u.username: u for u in users}
userid_table = {u.id: u for u in users}

def authenticate(username, password):
    user = username_table.get(username, None)
    if user and safe_str_cmp(user.password.encode('utf-8'), password.encode('utf-8')):
        return user

def identity(payload):
    user_id = payload['identity']
    return userid_table.get(user_id, None)


app = Flask(__name__)
app.debug = True
app.config['SECRET_KEY'] = 'super-secret'

jwt = JWT(app, authenticate, identity)

@app.route('/protected')
@jwt_required()
def protected():
    with open("static/cat.jpg", "rb") as image_file:
        encoded_string = base64.b64encode(image_file.read())
    now = datetime.now()
    current_time = now.strftime("%H:%M:%S")
    return jsonify({
        'time': current_time,
        'text': STATIC_STRING,
        'image': encoded_string.decode('utf-8')
        })

@app.route('/')
def index():
    return render_template('index.html')

if __name__ == '__main__':
    # app.run(host='192.168.0.100', ssl_context='adhoc')
    # app.run(host='192.168.0.100', ssl_context=('../rootCA.crt', '../rootCA.key'))
    app.run(host='192.168.0.100')