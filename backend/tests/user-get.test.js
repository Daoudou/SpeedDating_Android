const request = require('supertest');

test('GET /users', async () => {
  const res = await request(apiUrl)
    .get('/users');

  expect(res.statusCode).toEqual(200);
});
