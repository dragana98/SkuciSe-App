
exports.up = function(knex) {
  return knex.schema.createTable('userCredentials', tbl => {
      tbl.increments() //'id' field
      tbl.text('username', 16)
        .notNullable()
      tbl.text('password', 16)
        .notNullable()
      tbl.timestamps(true, true)
  })
  .createTable('userData', tbl => {
      tbl.increments() // 'id' field
      // OTHER FIELDS
      tbl.text('name', 32)
        .notNullable()
      tbl.text('surname', 32)
        .notNullable()
      tbl.text('contact_number', 32)
      tbl.timestamps(true, true) 
      // FOREIGN KEY TO userCredentials
      tbl.integer('user_id')
        .unsigned()
        .references('id')
        .inTable('userCredentials')
        .onDelete('CASCADE')
        .onUpdate('CASCADE')
  })
};

exports.down = function(knex) {
  return knex.schema.dropTableIfExists('userCredentials').dropTableIfExists('userData');
};
