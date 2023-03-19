import { test, expect } from '@playwright/test';
import percySnapshot from '@percy/playwright';
import handler from 'serve-handler';
import http from 'http';

test('open the report and take a screenshot', async ({ page }) => {
  const server = http.createServer((request, response) => {
    return handler(request, response, { public: '/Users/m/workspaces/github/tests-execution-chart/build/html-report' });
  });

  server.listen(3000);
  await page.goto('http://localhost:3000/task1.html');

  await expect(page).toHaveTitle(/Test execution chart/);
  await percySnapshot(page, 'Example Site');
});
