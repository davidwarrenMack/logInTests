# Play Java Product with JPA

This part of the project handles user management and security. We've used Amazon Web Services (AWS) to aid in the
security of our app by using their SMS text utility. Users will enter a valid cell phone number upon account creation
and that cell number is sent a random 4 digit authentication code that we'll use, along with encrypted password and
salt, to allow or deny access. All SQL variables have been bound to prevent SQL injection attacks. All session
variables are cleared when the account logs.

