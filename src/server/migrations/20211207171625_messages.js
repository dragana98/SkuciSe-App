exports.up = function (knex) {
    return knex.schema.createTable('messages', (tbl) => {
        tbl.increments()
        tbl.integer('usnd').notNullable()
            .references('username').inTable('users')
            .onDelete('CASCADE').onUpdate('CASCADE')

        tbl.integer('urcv').notNullable()
            .references('username').inTable('users')
            .onDelete('CASCADE').onUpdate('CASCADE')

        tbl.text('contents', 255).notNullable()
        tbl.text('date', 255).notNullable()
    })
};

exports.down = function (knex) {
    return knex.schema.dropTableIfExists('messages');
};