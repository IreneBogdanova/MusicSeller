<div class="bs-callout bs-callout-danger">

### Database Configuration is Required

If you haven't already, then please follow the steps below to configure your database connection and run the necessary migrations.

* Create the database for your application.
* Update the connection URL in the `profiles.clj` file with your database name and login.
* Run `lein run migrate` in the root of the project to create the tables.
* Let `mount` know to start the database connection by `require`-ing music-shop.db.core in some other namespace.
* Restart the application.

</div>


### Managing Your Middleware

Request middleware functions are located under the `music_shop.middleware` namespace.

This namespace is reserved for any custom middleware for the application. Some default middleware is
already defined here. The middleware is assembled in the `wrap-base` function.

Middleware used for development is placed in the `music-shop.dev-middleware` namespace found in
the `env/dev/clj/` source path.
