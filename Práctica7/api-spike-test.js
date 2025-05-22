import http from 'k6/http';
import { sleep } from 'k6';
import { check } from 'k6';
import { Rate } from 'k6/metrics';

const failRate = new Rate('failed_requests');

const spikeVUs = 3972;

export const options = {
    stages: [
        { duration: '2m', target: spikeVUs },    
        { duration: '2m', target: 0 },         
    ],
    
    thresholds: {
        'failed_requests': ['rate<0.005'],
        'checks': ['rate>0.995'],
        'http_req_duration': ['p(95)<1000'],  
    },
    
    abortOnFail: true,
};

export default () => {
    const urlRes = http.get('http://localhost:8080/medico/1');
    
    const checkResults = check(urlRes, {
        'status is 2xx': (r) => r.status >= 200 && r.status < 300,
        'response time < 800ms': (r) => r.timings.duration < 800,
        'response contains valid data': (r) => r.body.indexOf('"id":1') !== -1,
    });
    
    failRate.add(!checkResults);
    
    if (!checkResults) {
        console.error(`Spike test request failed with status ${urlRes.status} and duration ${urlRes.timings.duration}ms`);
    }
    
    sleep(0.5);
};