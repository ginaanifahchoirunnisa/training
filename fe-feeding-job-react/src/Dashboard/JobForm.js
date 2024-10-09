import React, { useState } from "react";
import "../index.css";
import { Modal, Input, Form, Button, Radio } from "antd";
import "../index.css";
import axios from "axios";

const JobForm = ({ open, closeModal, setStatusSaved }) => {
  const [jobData, setJobData] = useState({
    companyName: "",
    title: "",
    location: "",
    desc: "",
    skills: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setJobData({
      ...jobData,
      [name]: value,
    });
  };

  const addJobToDatabase = async (jobdata) => {
    try {
      const response = await axios.post(
        "http://localhost:3000/api/jobs",
        jobdata
      );
      closeModal();
      setStatusSaved();

      console.log("job added : ", response.data);
    } catch (error) {
      console.log("error appear ", error);
    }
  };

  if (!open) return null;
  return (
    <>
      <Modal
        title="Input New Job"
        centered
        open={open}
        footer={null}
        width={1000}
        closable={true}
        onCancel={closeModal}
      >
        <Form
          name="layout-multiple-horizontal"
          layout="horizontal"
          labelCol={{
            span: 4,
          }}
          wrapperCol={{
            span: 20,
          }}
        >
          <Form.Item
            label="Company Name"
            rules={[
              {
                required: true,
              },
            ]}
          >
            <Input
              name="companyName"
              value={jobData.companyName}
              onChange={handleChange}
            />
          </Form.Item>
          <Form.Item
            label="Title"
            rules={[
              {
                required: true,
              },
            ]}
          >
            <Input name="title" onChange={handleChange} value={jobData.title} />
          </Form.Item>
          <Form.Item label="Description" rules={[{ required: "true" }]}>
            <Input name="desc" onChange={handleChange} value={jobData.desc} />
          </Form.Item>
          <Form.Item label="Location" rules={[{ required: "true" }]}>
            <Radio.Group
              defaultValue={jobData.location}
              onChange={handleChange}
              name="location"
            >
              <Radio.Button value="bandung">Bandung</Radio.Button>
              <Radio.Button value="jakarta">Jakarta</Radio.Button>
              <Radio.Button value="bogor">Bogor</Radio.Button>
              <Radio.Button value="depok">Depok</Radio.Button>
            </Radio.Group>
          </Form.Item>
          <Form.Item label="Skills" rules={[{ required: "true" }]}>
            <Input
              name="skills"
              value={jobData.skills}
              onChange={handleChange}
            />
          </Form.Item>

          <Form.Item>
            <Button
              type="primary"
              htmlType="submit"
              onClick={() => {
                addJobToDatabase(jobData);
                console.log("the data => ", jobData);
              }}
            >
              Save Data
            </Button>
          </Form.Item>
        </Form>
      </Modal>
    </>
  );
};
export default JobForm;
