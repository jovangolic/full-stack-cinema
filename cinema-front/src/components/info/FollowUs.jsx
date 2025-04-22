import React from 'react';
import { Card, Col, Container, Row } from 'react-bootstrap';
import { FaFacebook, FaInstagram, FaTwitter, FaYoutube, FaTiktok } from "react-icons/fa";
import Header from '../common/Header';


const followUs = () => {
  return (
    <Container className="mt-5">
      <Header title={"Contact service"} />
      <hr/>
      <Row xs={1} md={2} lg={3} className="g-4 mt-2">
        <Col>
          <Card>
            <Card.Body>
              <Card.Title className='hotel-color'>
                <a href="https://www.facebook.com" target="_blank" rel="noopener noreferrer" className="text-decoration-none text-dark">
                  <FaFacebook /> Facebook
                </a>
              </Card.Title>
              <Card.Text>
                <a href="https://www.facebook.com" target="_blank" rel="noopener noreferrer" className="text-decoration-none">
                  Find us on Facebook
                </a>
              </Card.Text>
            </Card.Body>
          </Card>
        </Col>
        <Col>
          <Card>
            <Card.Body>
              <Card.Title className='hotel-color'>
                <a href="https://www.instagram.com" target="_blank" rel="noopener noreferrer" className="text-decoration-none text-dark">
                  <FaInstagram /> Instagram
                </a>
              </Card.Title>
              <Card.Text className='notel-color'>
                <a href="https://www.instagram.com" target="_blank" rel="noopener noreferrer" className="text-decoration-none">
                  Find us on Instagram
                </a>
              </Card.Text>
            </Card.Body>
          </Card>
        </Col>
        <Col>
          <Card>
            <Card.Body>
              <Card.Title className='hotel-color'>
                <a href="https://www.twitter.com" target="_blank" rel="noopener noreferrer" className="text-decoration-none text-dark">
                  <FaTwitter /> Twitter/X
                </a>
              </Card.Title>
              <Card.Text className='notel-color'>
                <a href="https://www.twitter.com" target="_blank" rel="noopener noreferrer" className="text-decoration-none">
                  Find us on Twitter/X
                </a>
              </Card.Text>
            </Card.Body>
          </Card>
        </Col>
        <Col>
          <Card>
            <Card.Body>
              <Card.Title className='hotel-color'>
                  <a href="https://youtube.com" target="_blank" rel="noopener noreferrer" className='text-decoration-none text-dark' >
                    <FaYoutube /> YouTube
                  </a>
                </Card.Title>
                <Card.Text className='notel-color'>
                  <a href="https://youtube.com" target="_blank" rel="noopener noreferrer" className='text-decoration-none'>
                    Find us on YouTube
                  </a>
                </Card.Text>
            </Card.Body>
          </Card>
        </Col>
        <Col>
          <Card>
            <Card.Body>
              <Card.Title className='hotel-color'>
                  <a href="https://tiktok.com" target="_blank" rel="noopener noreferrer" className='text-decoration-none text-dark' >
                    <FaTiktok /> TikTok
                  </a>
                </Card.Title>
                <Card.Text className='notel-color'>
                  <a href="https://tiktok.com" target="_blank" rel="noopener noreferrer" className='text-decoration-none'>
                    Find us on TikTok
                  </a>
                </Card.Text>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default followUs;
