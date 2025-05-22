import http from 'k6/http';
import { sleep } from 'k6';
import { check, fail } from 'k6';
import { Rate } from 'k6/metrics';

const failRate = new Rate('failed_requests');

export const options = {
    vus: 5,
    duration: '1m',
    thresholds: {
        'http_req_duration': ['avg<100'],
        'failed_requests': ['rate==0'],
        'checks': ['rate==1.0'],
    },

    abortOnFail: true,
};

export default () => {
    const urlRes = http.get('http://localhost:8080/medico/1');
    
    const checkResults = check(urlRes, {
        'status is 2xx': (r) => r.status >= 200 && r.status < 300,
    });
    
    failRate.add(!checkResults);
    
    if (!checkResults) {
        console.error(`Request failed with status ${urlRes.status}`);
    }
    
    sleep(1);
};