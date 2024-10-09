import React, { useEffect, useState } from "react";
import "../index.css";
import { Modal, Input, Form, Button, Radio } from "antd";
import "../index.css";
import axios from "axios";
import TextArea from "antd/es/input/TextArea";

const EditForm = ({
  isEditModal,
  closeEditModal,
  id,
  setAcceptedUpdateJob,
}) => {
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

  const updateJobToDatabase = async (jobdata) => {
    try {
      const response = await axios.put(
        `http://localhost:3000/api/jobs/${id}`,
        jobdata
      );
      closeEditModal();
      setAcceptedUpdateJob();
      console.log("the data => ", jobData);

      //   console.log("job added : ", response.data);
    } catch (error) {
      console.log("error appear ", error);
    }
  };

  useEffect(() => {
    const getDataJob = async () => {
      try {
        const res = await axios.get(`http://localhost:3000/api/jobs/${id}`);
        setJobData({
          companyName: res.data.companyName,
          title: res.data.title,
          location: res.data.location,
          desc: res.data.desc,
          skills: res.data.skills,
        });
      } catch (error) {
        console.log("error appear ", error);
      }
    };

    getDataJob();
  }, []);

  if (!isEditModal) return null;
  return (
    <>
      <Modal
        title="Edit job Data"
        centered
        open={isEditModal}
        footer={null}
        width={1000}
        closable={true}
        onCancel={closeEditModal}
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
            <TextArea
              name="desc"
              onChange={handleChange}
              style={{
                height: 120, // Set a fixed height
                overflowY: "scroll", // Enable vertical scrolling
              }}
              value={jobData.desc}
            />
          </Form.Item>
          <Form.Item label="Location" rules={[{ required: "true" }]}>
            <Radio.Group
              value={jobData.location}
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
            <TextArea
              name="skills"
              value={jobData.skills}
              onChange={handleChange}
              style={{
                height: 120, // Set a fixed height
                overflowY: "scroll", // Enable vertical scrolling
              }}
            />
          </Form.Item>

          <Form.Item>
            <Button
              type="primary"
              htmlType="submit"
              onClick={() => {
                updateJobToDatabase(jobData);
                console.log("the data => ", jobData);
              }}
            >
              Update Data
            </Button>
          </Form.Item>
        </Form>
      </Modal>
    </>
  );
};
export default EditForm;
