import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { Box } from "@mui/material";
import { Divider, Typography, Button, Alert } from "antd";

import { ArrowLeftOutlined } from "@ant-design/icons";
import axios from "axios";
import { useNavigate } from "react-router-dom";
const { Title } = Typography;

const Detail = () => {
  const { id } = useParams(); // Get the id parameter from the URL
  const [jobData, setJobData] = useState({}); // Initialize as an empty object
  const [isDeleted, setIsDeleted] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const getJobDetailById = async () => {
      try {
        const dataJob = await axios.get(`http://localhost:3000/api/jobs/${id}`);
        console.log("Job data => ", dataJob.data);
        setJobData(dataJob.data);
      } catch (error) {
        console.log("Error fetching job details:", error);
      }
    };

    getJobDetailById();
  }, [id]);

  return (
    <>
      {isDeleted && <Alert message="Success Tips" type="success" showIcon />}
      <Box
        sx={{
          height: "auto",
          width: "auto",
          bgcolor: "rgba(255, 255, 255, 0.3)",
          display: "flex",
          justifyContent: "flex-start",
          flexDirection: "column",
          alignItems: "flex-start",
          boxShadow: "0 4px 8px rgba(0, 0, 0, 0.2)",
          borderRadius: "8px",
        }}
      >
        {/* Dashboard Section */}
        <Box
          sx={{
            width: "98%",
            bgcolor: "#001F3F",
            textAlign: "left",
            border: "1px solid #e0e0e0",
            borderRadius: "8px",
            p: 2,
            mb: 2,
            boxShadow: "0 2px 4px rgba(0, 0, 0, 0.1)",
          }}
        >
          <h6 style={{ margin: 0, color: "white" }}>Job Detail </h6>
        </Box>

        {/* Job List */}

        <Box
          sx={{
            display: "flex",
            flexDirection: "column",
            width: "60%",
            justifyContent: "space-between",
            bgcolor: "#faffff",
            border: "0.2px solid grey",

            mt: 2,
            ml: 25,
            mr: 25,
            p: 10,

            borderRadius: "50px",
            boxShadow: "0 2px 4px rgba(0, 0, 0, 0.1)",
          }}
        >
          <Button
            style={{ width: "10%" }}
            onClick={() => {
              navigate("/dashboard");
            }}
          >
            <ArrowLeftOutlined />
            Back
          </Button>
          <h1 style={{ marginBottom: "0.1em", color: "#384B70" }}>
            Job Detail
          </h1>

          <Divider />

          {/* Render job data conditionally */}
          {jobData && (
            <>
              <Box>
                <Divider orientation="left">
                  <p style={{ fontWeight: "bold" }}>Company Name</p>
                </Divider>
                <p style={{ textAlign: "left", color: "grey" }}>
                  {jobData.companyName || "N/A"}
                </p>
              </Box>

              <Box>
                <Divider orientation="left">
                  <p style={{ fontWeight: "bold" }}>Title</p>
                </Divider>
                <p style={{ textAlign: "left", color: "grey" }}>
                  {jobData.title || "N/A"}
                </p>
              </Box>

              <Box>
                <Divider orientation="left">
                  <p style={{ fontWeight: "bold" }}>Description</p>
                </Divider>
                <p style={{ textAlign: "justify", color: "grey" }}>
                  {jobData.desc || "N/A"}
                </p>
              </Box>
              <Box>
                <Divider orientation="left">
                  <p style={{ fontWeight: "bold" }}>Location</p>
                </Divider>
                <p style={{ textAlign: "left", color: "grey" }}>
                  {jobData.location || "N/A"}
                </p>
              </Box>

              <Box>
                <Divider orientation="left">
                  <p style={{ fontWeight: "bold" }}>Skills</p>
                </Divider>
                <p style={{ textAlign: "left", color: "grey" }}>
                  {jobData.skills || "N/A"}
                </p>
              </Box>
              {/* Add more job detail fields as needed */}
            </>
          )}
        </Box>
      </Box>
    </>
  );
};

export default Detail;
