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

        const buttonAdd = page.locator('button[name="add"]');

        await Promise.all([page.waitForNavigation(), buttonAdd.click()]);

        await page.locator('input[name="dni"]').type('15541574H');
        await page.locator('input[name="nombre"]').type('Eustaquio');
        await page.locator('input[name="edad"]').type('23');
        await page.locator('input[name="cita"]').type('ginecólogo');

        const submitButton = page.locator('button[type="submit"]');

        await Promise.all([page.waitForNavigation(), submitButton.click()]);

        await check(page.locator('h2'), {
            header: async (lo) => (await lo.textContent()) == "Listado de pacientes",
        });

        const datos = await page.evaluate(() => {
            const filas = document.querySelectorAll('table tbody tr');
            const ultimaFila = filas[filas.length - 1];
            if (!ultimaFila) return null;

            const celdas = ultimaFila.querySelectorAll('td');
            return {
                nombre: celdas[1]?.textContent.trim(),

            };
        });

        check(null, {
            'Eustaquio creado': () => datos?.nombre === 'Eustaquio',
        });

    } finally {
        await page.close();
    }
}