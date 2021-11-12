
exports.up = function(knex) {
  return knex.schema.createTable("realties", (tbl) => {
    tbl.increments() //id primary key AI
    tbl.text('name', 255).notNullable()
    tbl.text('pic_url')
    tbl.integer('price').notNullable()
    tbl.boolean('monthly').notNullable()
    tbl.integer('deposit').notNullable()
    tbl.integer('floor_area').notNullable()
    tbl.text('occupied').notNullable()
    tbl.text('username').notNullable()
      .references('username').inTable('users').onDelete('CASCADE').onUpdate('CASCADE')
    // tbl.text('listing_id').notNullable()
    //   .references('id').inTable('listings').onDelete('CASCADE').onUpdate('CASCADE')
  });
};

exports.down = function(knex) {
    return knex.schema.dropTableIfExists('realties');
};
