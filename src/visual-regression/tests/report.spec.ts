import { test, expect } from '@playwright/test';
import percySnapshot from '@percy/playwright';
import handler from 'serve-handler';
import http from 'http';

const port = 3000;
test.beforeEach(async ({ page }) => {
  const server = http.createServer((request, response) => {
    return handler(request, response, { public: '../../build/html-report' });
  });

  server.listen(port);
});


test('open the report and take a screenshot', async ({ page }) => {
  await page.goto(`http://localhost:${port}/test.html`);

  await expect(page).toHaveTitle(/Test execution chart/);
  await percySnapshot(page, 'Test execution chart page');
});
