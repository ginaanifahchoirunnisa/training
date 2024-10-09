import React, { useRef, useState, useEffect } from "react";
import {
  Button,
  Col,
  Card,
  Row,
  Pagination,
  Divider,
  Popconfirm,
  Alert,
  Form,
  Select,
  Input,
  Spin,
  Typography,
} from "antd";
import { Box } from "@mui/material";
import {
  DownloadOutlined,
  PlusOutlined,
  SearchOutlined,
} from "@ant-design/icons";
import axios from "axios";
import JobForm from "./JobForm";
import EditForm from "./EditForm";
import { useNavigate } from "react-router-dom";
const { Title } = Typography;

function Dashboard() {
  const [size, setSize] = useState("large");
  const [jobData, setJobData] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [pageSize, setPageSize] = useState(10);
  const navigate = useNavigate();
  const [isLoading, setIsLoading] = useState(true);
  const [acceptedDeleting, setAcceptedDeleting] = useState(false);
  const [open, setOpen] = useState(false);
  const [searchCriteria, setSearchCriteria] = useState();
  const [searchPrompt, setSearchPromt] = useState();
  const [isEditModal, setIsEditModal] = useState(false);
  const [idEdit, setIdEdit] = useState(0);
  const [isAddNewDataAction, setIsAddNewDataAction] = useState(false);
  const [acceptedUpdate, setAcceptedUpdate] = useState(false);

  // Fetch job data from API

  const getDataJobs = async () => {
    await axios
      .get("http://localhost:3000/api/jobs", { timeout: 10000 })
      .then((response) => {
        setJobData(response.data);
        setIsLoading(false);
      })
      .catch((error) => {
        console.error("Error fetching job data:", error);
      });
  };

  const getDataJobsBasedOnSearchCriteria = async () => {
    setIsLoading(true);
    const res = await axios
      .get(
        `http://localhost:3000/api/search?basedOn=${searchCriteria}&data=${searchPrompt}`
      )
      .then((res) => {
        setJobData(res.data);
        setIsLoading(false);
      })
      .catch((err) => {
        console.log("Error fetching job data ", err);
      });
  };

  useEffect(() => {
    getDataJobs();
  }, []);

  useEffect(() => {
    getDataJobs();
  }, [acceptedDeleting]);

  const setStatusSaved = () => {
    setIsAddNewDataAction(true);
    setTimeout(() => {
      setIsAddNewDataAction(false);
    }, 3000);
    setIsLoading(true);

    getDataJobs();
  };

  const resetSearch = () => {
    setIsLoading(true);
    getDataJobs();
  };

  const downloadExcel = async () => {
    axios({
      url: `http://localhost:3000/api/generateExcel?page=${currentPage}&limit=${pageSize}`, // API endpoint
      method: "GET",
      responseType: "blob", // Important to get the file as a Blob
    })
      .then((res) => {
        const url = window.URL.createObjectURL(new Blob([res.data]));
        const link = document.createElement("a");
        link.href = url;

        link.setAttribute("download", "jobs_data.xlsx");

        document.body.appendChild(link);

        link.click();

        document.body.removeChild(link);
      })
      .catch((err) => {
        console.error("Error downloading the file:", err);
      });
  };

  // Handle pagination change
  const handlePaginationChange = (page, pageSize) => {
    setCurrentPage(page);
    setPageSize(pageSize);
  };

  const contentStyle = {
    padding: 50,
    background: "rgba(0, 0, 0, 0.05)",
    borderRadius: 4,
  };
  const content = <div style={contentStyle} />;

  // Calculate the jobs to display based on current page and page size
  const paginatedJobData = jobData.slice(
    (currentPage - 1) * pageSize,
    currentPage * pageSize
  );

  const openModal = () => {
    setOpen(true);
  };

  const closeModal = () => {
    setOpen(false);
  };

  const openEditModal = () => {
    setIsEditModal(true);
  };
  const closeEditModal = () => {
    setIsEditModal(false);
  };

  const setAcceptedUpdateJob = () => {
    setAcceptedUpdate(true);
    const timer = setTimeout(() => {
      setAcceptedUpdate(false);
    }, 3000);
    setIsLoading(true);
    getDataJobs();
  };
  const deleteCurrJob = async (id) => {
    try {
      const res = await axios.delete(`http://localhost:3000/api/jobs/${id}`);
      setAcceptedDeleting(true);

      const timer = setTimeout(() => {
        setAcceptedDeleting(false);
      }, 3000);

      setIsLoading(true);
      getDataJobs();
    } catch (error) {
      console.log("error appear", error);
    }
  };

  return (
    <>
      <Box
        sx={{
          height: "auto",
          width: "auto",
          bgcolor: "rgba(255, 255, 255, 0.8)",
          display: "flex",
          justifyContent: "flex-start",
          flexDirection: "column",
          pb: "23%",
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
            color: "white",
            textAlign: "left",
            border: "1px solid #e0e0e0",
            borderRadius: "8px",
            p: 2,
            mb: 2,
            boxShadow: "0 2px 4px rgba(0, 0, 0, 0.1)",
          }}
        >
          <h6 style={{ margin: 0 }}>Dashboard</h6>
        </Box>

        {acceptedDeleting && (
          <Box sx={{ ml: 2 }}>
            <Alert
              message="Job data has been deleted"
              type="success"
              showIcon
            />
          </Box>
        )}

        {acceptedUpdate && (
          <Box sx={{ ml: 2 }}>
            <Alert
              message="Job data has been updated"
              type="success"
              showIcon
            />
          </Box>
        )}

        {isAddNewDataAction && (
          <Box sx={{ ml: 2 }}>
            <Alert message="Job data has been added" type="success" showIcon />
          </Box>
        )}

        {/* Section One */}
        <Box
          sx={{
            display: "flex",
            flexDirection: "row",

            width: "100%",
            justifyContent: "space-between",
            alignItems: "center",
            mb: 2,
          }}
        >
          <Box sx={{ pl: 2 }}>
            <div style={{ fontSize: "1.5rem" }}>
              <Title level={1} style={{ fontSize: "1.2em", color: "#001F3F" }}>
                Feeding Job
              </Title>
            </div>
          </Box>

          <Form>
            <Row gutter={16}>
              <Col>
                <Form.Item
                  label="Searching Based On"
                  name="select"
                  rules={[
                    { required: true, message: "Please select an option!" },
                  ]}
                >
                  <Select
                    value={searchCriteria}
                    onChange={(value) => {
                      console.log("terpilih ", value);
                      setSearchCriteria(value);
                    }}
                    style={{
                      width: 200,
                    }}
                    allowClear
                    options={[
                      {
                        value: "location",
                        label: "Location",
                      },
                      {
                        value: "skills",
                        label: "Skills",
                      },
                      {
                        value: "company_name",
                        label: "Company Name",
                      },
                    ]}
                    placeholder="Select this"
                  />
                </Form.Item>
              </Col>
              <Col>
                <Form.Item
                  label="Searched Prompt"
                  name="search"
                  rules={[
                    { required: true, message: "Please enter a search term!" },
                  ]}
                >
                  <Input
                    name="search"
                    onChange={(e) => {
                      console.log(e.target.value);
                      setSearchPromt(e.target.value);
                    }}
                    value={jobData.desc}
                  />
                </Form.Item>
              </Col>
              <Col>
                <Form.Item>
                  <Button
                    htmlType="submit"
                    type="primary"
                    onClick={getDataJobsBasedOnSearchCriteria}
                  >
                    Search <SearchOutlined />
                  </Button>
                </Form.Item>
              </Col>
              <Col>
                <Form.Item>
                  <Button
                    htmlType="submit"
                    type="primary"
                    onClick={resetSearch}
                  >
                    Reset
                  </Button>
                </Form.Item>
              </Col>
            </Row>
          </Form>

          <Box
            sx={{
              display: "flex",
              gap: "1rem",
              pr: 2,
            }}
          >
            <Button
              onClick={downloadExcel}
              icon={<DownloadOutlined />}
              size={size}
              type="primary"
            >
              Download Excel
            </Button>

            <Button
              type="primary"
              icon={<PlusOutlined />}
              onClick={openModal}
              size={size}
            >
              Add Jobs
            </Button>
          </Box>
        </Box>

        {/* Job List */}
        <Box
          sx={{
            display: "flex",
            flexDirection: "column",
            width: "94%",
            justifyContent: "space-between",
            bgcolor: "#f4fdfe",
            mt: 2,
            ml: 3,
            mr: 3,
            p: 2,
            borderRadius: "8px",
            border: "1px solid #e0e0e0",
            boxShadow: "0 2px 4px rgba(0, 0, 0, 0.1)",
          }}
        >
          <h1 style={{ marginBottom: "0.1em", color: "#001F3F" }}>Job Lists</h1>

          {isLoading && (
            <Spin tip="Loading" size="large">
              {content}
            </Spin>
          )}
          {!isLoading && (
            <>
              <Divider style={{ marginBottom: "3em" }}></Divider>

              <Row gutter={[16, 16]}>
                {paginatedJobData.map((job) => (
                  <Col key={job.id} className="gutter-row" span={6}>
                    <Card
                      title={job.companyName}
                      bordered={false}
                      style={{
                        height: "300px",
                        borderColor: "#384B70",
                        borderWidth: 1,
                        borderStyle: "solid",
                        transition: "transform 0.2s",
                        borderRadius: "8px",
                      }}
                      hoverable
                      onMouseEnter={(e) => {
                        e.currentTarget.style.transform = "scale(1.05)";
                        e.currentTarget.style.backgroundColor = "#eaf7ff";
                      }}
                      onMouseLeave={(e) => {
                        e.currentTarget.style.transform = "scale(1)";
                        e.currentTarget.style.backgroundColor = "white";
                      }}
                    >
                      <div className="max-w-sm p-6">
                        <h5 className="mb-2 text-2xl font-bold tracking-tight text-gray-900">
                          {job.title}
                        </h5>

                        <p className="mb-3 font-normal text-gray-700">
                          {job.skills}
                        </p>
                        <Button
                          type="link"
                          onClick={() => {
                            navigate(`/detail/${job.id}`);
                          }}
                          style={{
                            padding: 6,
                            border: "none",
                            color: "blue",
                            fontSize: "12px",
                            transition: "font-size 0.2s ease", // Smooth transition
                          }}
                          onMouseOver={(e) => {
                            e.currentTarget.style.fontSize = "14px"; // Increase font size on hover
                          }}
                          onMouseOut={(e) => {
                            e.currentTarget.style.fontSize = "12px"; // Reset font size on mouse out
                          }}
                        >
                          Detail
                        </Button>
                        <Button
                          type="link"
                          onClick={() => {
                            setIdEdit(job.id);
                            openEditModal();
                            console.log("---- ", isEditModal);
                          }}
                          style={{
                            padding: 6,
                            border: "none",
                            pl: "10px",
                            color: "#FF6600",
                            fontSize: "12px",
                            transition: "font-size 0.2s ease",
                          }}
                          onMouseOver={(e) => {
                            e.currentTarget.style.fontSize = "14px";
                          }}
                          onMouseOut={(e) => {
                            e.currentTarget.style.fontSize = "12px";
                          }}
                        >
                          Edit
                        </Button>
                        <Popconfirm
                          placement="rightTop"
                          title="Delete Data"
                          description="Are you sure you wanna delete this job data?"
                          okText="Yes"
                          cancelText="No"
                          onConfirm={() => deleteCurrJob(job.id)}
                        >
                          <Button
                            type="link"
                            style={{
                              padding: 6,
                              border: "none",
                              pl: "10px",
                              color: "red",
                              fontSize: "12px",
                              transition: "font-size 0.2s ease",
                            }}
                            onMouseOver={(e) => {
                              e.currentTarget.style.fontSize = "14px";
                            }}
                            onMouseOut={(e) => {
                              e.currentTarget.style.fontSize = "12px"; // Reset font size on mouse out
                            }}
                          >
                            Delete
                          </Button>
                        </Popconfirm>
                      </div>
                    </Card>
                  </Col>
                ))}
              </Row>

              {/* Pagination Component */}
              <Pagination
                current={currentPage}
                pageSize={pageSize}
                total={jobData.length}
                onChange={handlePaginationChange}
                style={{ marginTop: "20px", textAlign: "center" }}
              />
            </>
          )}
        </Box>

        <JobForm
          open={open}
          closeModal={closeModal}
          setStatusSaved={setStatusSaved}
        />
        {isEditModal && (
          <EditForm
            isEditModal={isEditModal}
            closeEditModal={closeEditModal}
            id={idEdit}
            setAcceptedUpdateJob={setAcceptedUpdateJob}
          />
        )}
      </Box>
    </>
  );
}

export default Dashboard;
