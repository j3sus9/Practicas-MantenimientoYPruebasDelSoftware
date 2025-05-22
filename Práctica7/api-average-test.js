import http from 'k6/http';
import { sleep } from 'k6';
import { check } from 'k6';
import { Rate } from 'k6/metrics';

const failRate = new Rate('failed_requests');
const targetVUs = 267;

export const options = {
    stages: [
        { duration: '3m', target: targetVUs }, 
        { duration: '3m', target: targetVUs }, 
        { duration: '2m', target: 0 },    
    ],
    
    thresholds: {
        'failed_requests': ['rate<0.01'],
        'checks': ['rate>0.99'],
    },
    
    abortOnFail: true,
};

export default () => {
    const urlRes = http.get('http://localhost:8080/medico/1');
    
    const checkResults = check(urlRes, {
        'status is 2xx': (r) => r.status >= 200 && r.status < 300,
        'response time < 500ms': (r) => r.timings.duration < 500,
    });

    failRate.add(!checkResults);
    
    if (!checkResults) {
        console.error(`Request failed with status ${urlRes.status} and duration ${urlRes.timings.duration}ms`);
    }
    
    sleep(1);
};