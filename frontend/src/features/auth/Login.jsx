import {useState} from "react";
import {Link} from "react-router-dom";
import Button from "../main/components/atoms/Button";
import SolarWatchLogo from "../main/components/atoms/SolarWatchLogo";

function Login() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();

        const body = {username, password}

        try {
            const response = await fetch(`/api/users/login`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(body),
            });

            if (!response.ok) {
                setError('Something went wrong');
            }

            const data = await response.json();
            console.log(data);
            setSuccess(data.message)
            localStorage.setItem('jwt', data.jwt);
            localStorage.setItem('userName', data.userName);
            localStorage.setItem('roles', data.roles);
            console.log(localStorage)

            
            setTimeout(() => {
                window.location.href = '/';
            }, 1000);

            console.log('Login successful', data);
        } catch (err) {
            setError(`Authentication failed. ${err}`);

            console.error(err);
        }
    }

    return (
        <div>
            <div className="flex min-h-full flex-col justify-center px-6 py-12 lg:px-8">
                <div className="sm:mx-auto sm:w-full sm:max-w-sm">
                    <SolarWatchLogo h={20}/>
                    <h2 className="mt-10 text-center text-2xl/9 font-bold tracking-tight">Log into
                        your
                        account</h2>
                </div>

                <div className="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
                    <form className="space-y-6" action="#" method="POST" onSubmit={e =>handleSubmit(e)}>
                        <div>
                            <div className="flex items-start">
                                <label htmlFor="username" className="block text-sm/6 font-medium">
                                    Name</label>
                            </div>
                            <div className="mt-2">
                                <input id="username" name="username" type="text" autoComplete="email" required onChange={(e) => setUsername(e.target.value)}
                                       className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm/6"/>
                            </div>
                        </div>

                        <div>
                            <div className="flex items-start">
                                <label htmlFor="password"
                                       className="block text-sm/6 font-medium">Password</label>
                            </div>
                            <div className="mt-2">
                                <input id="password" name="password" type="password" autoComplete="current-password"
                                       required onChange={(e) => setPassword(e.target.value)}
                                       className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm/6"/>
                            </div>
                        </div>

                        <div>
                            <Button type="submit"
                                    className="flex w-full justify-center rounded-md px-3 py-1.5 text-sm/6 font-semibold text-black shadow-sm focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
                                Log in
                            </Button>
                        </div>
                    </form>

                    {error && <p className="text-red-500">{error}</p>}
                    {success && <p className="text-green-500">{success}</p>}

                    <p className="mt-10 text-center text-sm/6 text-light-mutedText dark:text-dark-mutedText">
                        Don&#39;t have an account yet?
                        <Link className="font-semibold text-accent hover:text-indigo-500" to="/register"> Register</Link>

                    </p>
                </div>
            </div>
        </div>
    )
}

export default Login;