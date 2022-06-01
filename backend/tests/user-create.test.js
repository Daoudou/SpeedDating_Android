const request = require('supertest');

test('POST /users => success', async () => {
  const res = await request(apiUrl)
    .post('/users')
    .send({
      firstName: 'Blablabla',
      lastName: 'Blublublu',
      password: 'Blobloblo',
    });

  expect(res.statusCode).toEqual(201);
});

test('POST /users => without "lastName" should fail', async () => {
  const res = await request(apiUrl)
    .post('/users')
    .send({
      firstName: 'Blablabla',
      password: 'Blobloblo',
    });

  expect(res.statusCode).toEqual(500);
  expect(res.text).toEqual('Property "req.body.lastName": Invalid value. Current value = undefined');
});

test('POST /users', async () => {
  const res = await request(apiUrl)
    .post('/users')
    .send({
      firstName: 'Blablabla',
      lastName: 'Blublublu',
      password: 'oups',
    });

  expect(res.statusCode).toEqual(500);
  expect(res.text).toEqual('Property "req.body.password": Invalid value. Current value = oups');
});
