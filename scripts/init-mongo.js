db = db.getSiblingDB('ChessRating');
db.players.insertMany([
    {_id: ObjectId('59695d12-ab80-4538-b6ef-2db5fdf6e905'), firstName: 'Anton', lastName: 'Kosohov', email: 'anton.kosohov@gmail.com', chessData: { rating: 1700 }, _class: 'com.chessrating.model.Player'},
    {_id: ObjectId('99611c7d-5c7d-4f23-8619-8aaf04ca9421'), firstName: 'Maryna', lastName: 'Kosohova', email: 'maryna.kosohova@gmail.com', chessData: { rating: 1600 }, _class: 'com.chessrating.model.Player'},
    {_id: ObjectId('12345678-ab80-4538-b6ef-2db5fdf6e905'), firstName: 'Insha', lastName: 'Cat', email: 'insha.cat@gmail.com', chessData: { rating: 1500 }, _class: 'com.chessrating.model.Player'}
]);
