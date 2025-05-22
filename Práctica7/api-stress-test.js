import http from 'k6/http';
import { check } from 'k6';

export const options = {
  stages: [
    { duration: '3m', target: 7943 }, 
    { duration: '3m', target: 7943 },
    { duration: '2m', target: 0 },    
  ],
  thresholds: {
    http_req_failed: [
      { threshold: 'rate<=0.01', abortOnFail: true }, 
    ],
    http_req_duration: [       
      { threshold: 'avg<=1000', abortOnFail: true },
    ],
  },
};

export default function () {
  const res = http.get('http://localhost:8080/medico/1');
  check(res, {
    'status is 200': (r) => r.status === 200,
  });
}