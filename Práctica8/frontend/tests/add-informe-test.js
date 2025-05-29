/*
Jesús Repiso Rio
Alejandro Cueto Díaz
*/

import { browser } from 'k6/browser';
import { check } from 'https://jslib.k6.io/k6-utils/1.5.0/index.js';

export const options = {
    scenarios: {
        ui: {
            executor: 'shared-iterations',
            options: { browser: { type: 'chromium' } },
            vus: 1,
            iterations: 1,
        },
    },
};

export default async function () {
    const page = await browser.newPage();
    try {
        await page.goto('http://localhost:4200/');

        await page.locator('input[name="nombre"]').type('admin');
        await page.locator('input[name="DNI"]').type('123');

        const buttonLogin = page.locator('button[name="login"]');

        await Promise.all([page.waitForNavigation(), buttonLogin.click()]);

        await check(page.locator('h2'), {
            header: async (lo) => (await lo.textContent()) == "Listado de pacientes",
        });

        //Pinchar en el paciente
        await page.waitForSelector('td[name="nombre"]');

        // Usar evaluate para forzar click en la primera celda
        await page.evaluate(() => {
            const celdas = document.querySelectorAll('td[name="nombre"]');
            if (celdas.length > 0) {
                const evt = new MouseEvent('click', { bubbles: true, cancelable: true });
                celdas[0].dispatchEvent(evt);
            }
        });

        const buttonImage = page.locator('button[name="view"]');
        await Promise.all([page.waitForNavigation(), buttonImage.click()]);

        const buttonAddInforme = page.locator('button[name="add"]');
        await Promise.all([page.waitForNavigation(), buttonAddInforme.click()]);

        const buttonScore = page.locator('button[name="predict"]');
        await buttonScore.click();

        await page.waitForSelector('div.result');

        await check(page.locator('div.result'), {
            hasPrediction: async (lo) =>
                (await lo.textContent())?.includes('Probabilidad de cáncer'),
        });

        await page.locator('textarea').type('Resultado esperado');

        const buttonSave = page.locator('button[name="save"]');
        await Promise.all([page.waitForNavigation(), buttonSave.click()]);

        await check(page.locator('h1'), {
            header: async (lo) => (await lo.textContent()) == "Informe de la imagen",
        });
    } finally {
        await page.close();
    }
}