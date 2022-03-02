#!/usr/bin/python

import sys
import logging

logging.basicConfig(stream=sys.stderr)

# load the virtual environment venv
activate_this = '/var/www/FlaskApp/FlaskApp/venv/bin/activate_this.py'
with open(activate_this) as file_:
    exec(file_.read(), dict(__file__=activate_this))

sys.path.insert(0,"/var/www/FlaskApp/")

from FlaskApp import app as application

application.secret_key = '0c70b9949d4f4c404e117626def951b4'
