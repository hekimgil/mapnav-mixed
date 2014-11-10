# This file is used by Rack-based servers to start the application.

#require ::File.expand_path('../config/environment',  __FILE__)
#run Rails.application

WEB_SUBDIR = 'web'
require "#{WEB_SUBDIR}/config/environment"
#require ::File.expand_path('../config/environment',  __FILE__)
run Rails.application
