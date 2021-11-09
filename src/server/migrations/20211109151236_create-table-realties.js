
exports.up = function(knex) {
  return knex.schema.createTable("realties", (tbl) => {
    tbl.increments() //id primary key AI
    tbl.text('name', 255).notNullable()
    tbl.text('approx_location', 255)
    tbl.float('floor_size', 4, 1).notNullable()
    tbl.float('avg_rating', 2, 1).notNullable()
    tbl.text('description', 512)
    tbl.integer('owner_id').unsigned().notNullable()
    .references('id').inTable('users').onDelete('CASCADE').onUpdate('CASCADE')
  });
};

exports.down = function(knex) {
    return knex.schema.dropTableIfExists('realties');
};
