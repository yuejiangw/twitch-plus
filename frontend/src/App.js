import React, { useState } from 'react';
import { Layout, message } from 'antd';
import { logout } from './utils';
import PageHeader from './components/PageHeader';

const { Header, Content, Sider } = Layout;

function App() {
    const [loggedIn, setLoggedIn] = useState(false)

    const signinOnSuccess = () => {
        setLoggedIn(true);
    }

    const signoutOnClick = () => {
        logout().then(() => {
            setLoggedIn(false)
            message.success('Successfully Signed out')
        }).catch((err) => {
            message.error(err.message)
        })
    }

    return (
        <Layout>
            <Header>
                <PageHeader
                    loggedIn={loggedIn}
                    signoutOnClick={signoutOnClick}
                    signinOnSuccess={signinOnSuccess}
                />
            </Header>
            <Layout>
                <Sider width={300} className="site-layout-background">
                    {'Sider'}
                </Sider>
                <Layout style={{ padding: '24px' }}>
                    <Content
                        className="site-layout-background"
                        style={{
                            padding: 24,
                            margin: 0,
                            height: 800,
                            overflow: 'auto'
                        }}
                    >
                        {'Home'}
                    </Content>
                </Layout>
            </Layout>
        </Layout>
    )
}

export default App;