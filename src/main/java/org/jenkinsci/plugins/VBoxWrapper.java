package org.jenkinsci.plugins;
import hudson.Launcher;
import hudson.Extension;
import hudson.util.FormValidation;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.AbstractProject;
import hudson.model.Descriptor;
import hudson.model.Run.RunnerAbortedException;
import hudson.tasks.BuildStep;
import hudson.tasks.BuildWrapper;
import hudson.tasks.Builder;
import hudson.tasks.BuildStepDescriptor;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.QueryParameter;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Sample {@link Builder}.
 *
 * <p>
 * When the user configures the project and enables this builder,
 * {@link DescriptorImpl#newInstance(StaplerRequest)} is invoked
 * and a new {@link VBoxWrapper} is created. The created
 * instance is persisted to the project configuration XML by using
 * XStream, so this allows you to use instance fields (like {@link #name})
 * to remember the configuration.
 *
 * <p>
 * When a build is performed, the {@link #perform(AbstractBuild, Launcher, BuildListener)}
 * method will be invoked. 
 *
 * @author Kohsuke Kawaguchi
 */

@SuppressWarnings("rawtypes") 
public class VBoxWrapper extends BuildWrapper {

    private final ArrayList<BuildStep> vboxSetupSteps;
    private final ArrayList<BuildStep> vboxTeardownSteps;

    // Fields in config.jelly must match the parameter names in the "DataBoundConstructor"
    @DataBoundConstructor
    public VBoxWrapper(ArrayList<BuildStep> vboxSetupSteps, 
    		ArrayList<BuildStep> vboxTeardownSteps) {
        this.vboxSetupSteps = vboxSetupSteps;
        this.vboxTeardownSteps = vboxTeardownSteps;
    }
    
    /* 
     * Perform decorator task
	 * @see hudson.tasks.BuildWrapper#decorateLauncher(hudson.model.AbstractBuild, hudson.Launcher, hudson.model.BuildListener)
	 */
	@Override
	public Launcher decorateLauncher(AbstractBuild build, Launcher launcher,
			BuildListener listener) throws IOException, InterruptedException,
			RunnerAbortedException {
		// TODO Auto-generated method stub
		
		return super.decorateLauncher(build, launcher, listener);
	}



	/**
     * Descriptor for {@link VBoxWrapper}. Used as a singleton.
     * The class is marked as public so that it can be accessed from views.
     *
     * <p>
     * See <tt>src/main/resources/hudson/plugins/hello_world/HelloWorldBuilder/*.jelly</tt>
     * for the actual HTML fragment for the configuration screen.
     */
    @Extension // This indicates to Jenkins that this is an implementation of an extension point.
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {
   
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            // Indicates that this builder can be used with all kinds of project types 
            return true;
        }

        /**
         * This human readable name is used in the configuration screen.
         */
        public String getDisplayName() {
            return "VirtualBox setup/teardown tasks";
        }

    }
}

