exports.up = function(knex) {
    return knex.schema.createTable("users", (tbl) => {
      tbl.increments() //id
      tbl.text("username", 128).notNullable().unique().index()
      tbl.text("password", 255).notNullable().notNullable()
    })
    
    .createTable("userData", (tbl) => {
      tbl.increments()
      tbl.text('name', 128).notNullable()
      tbl.text('surname', 128).notNullable()
      tbl.text('date_of_birth', 128).notNullable()
      tbl.text('phone_number', 128).notNullable()
      tbl.text('document_type', 255).notNullable()
      tbl.integer('document_number').notNullable()
      tbl.text('avatar_url', 255)
      
      tbl.text('username')
        .notNullable().index().unique()
        .references('username').inTable('users')
        .onDelete('CASCADE').onUpdate('CASCADE')
    });
  };
  
  exports.down = function(knex) {
    return knex.schema.dropTableIfExists("users").dropTableIfExists("userData");
  };
  