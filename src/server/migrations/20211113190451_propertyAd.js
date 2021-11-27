
exports.up = function(knex) {
    return knex.schema.createTable("propertyAd", (tbl) => {
        tbl.increments()
        tbl.integer('realtor_id').notNullable()
          .references('id').inTable('realtors').onDelete('CASCADE').onUpdate('CASCADE')
        tbl.text('name', 255).notNullable()
        tbl.text('description', 255).notNullable()
        tbl.text('city', 255).notNullable()
        tbl.integer('postal_code').notNullable()
        tbl.text('street_address', 255).notNullable()
        tbl.integer('leasable').notNullable()
        tbl.integer('unified').notNullable()
      });
};

exports.down = function(knex) {
    return knex.schema.dropTableIfExists('propertyAd');
};
