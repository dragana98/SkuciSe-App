//users table
exports.up = function(knex) {
    return knex.schema.createTable("users", (tbl) => {
      tbl.increments() //id
      tbl.text("username", 128).notNullable().unique().index()
      tbl.text("password", 255).notNullable().notNullable()
    })
    
    .createTable("userData", (tbl) => {
      tbl.increments()
      tbl.text('name', 128).notNullable()
      tbl.text('contact_number', 128)
      tbl.text('address', 255)
      tbl.text('avatar_url', 255)
      tbl.text('website_url', 255)
      // tbl.integer('user_id')
      //   .unsigned().notNullable()
      //   .references('id').inTable('users')
      //   .onDelete('CASCADE').onUpdate('CASCADE')
      tbl.text('username')
        .notNullable().index().unique()
        .references('username').inTable('users')
        .onDelete('CASCADE').onUpdate('CASCADE')
    });
  };
  
  exports.down = function(knex) {
    return knex.schema.dropTableIfExists("users").dropTableIfExists("userData");
  };
  