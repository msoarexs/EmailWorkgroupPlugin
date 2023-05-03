import sailpoint.object.Identity;
import sailpoint.object.Workgroup;
import sailpoint.plugin.PluginContext;
import sailpoint.plugin.PluginBase;
import sailpoint.tools.GeneralException;

public class EmailWorkgroupPlugin extends PluginBase {

    // Nome do workgroup de destino
    private static final String TARGET_WORKGROUP_NAME = "VISION Test Group";

    @Override
    public void init(PluginContext context) throws GeneralException {
        super.init(context);
        getLogger().info("EmailWorkgroupPlugin inicializado.");
    }

    @Override
    public String getName() {
        return "EmailWorkgroupPlugin";
    }

    @Override
    public String getDescription() {
        return "Plugin para adicionar usuários com e-mail contendo 'nicolasec' em um workgroup.";
    }

    @Override
    public void execute(PluginContext context) throws GeneralException {
        getLogger().info("Executando o plugin EmailWorkgroupPlugin.");

        // Obter todos os usuários do IdentityIQ
        Identity[] identities = context.getObjectManager().getObjects(Identity.class);

        // Obter o workgroup de destino pelo nome
        Workgroup targetWorkgroup = context.getObjectByName(Workgroup.class, TARGET_WORKGROUP_NAME);
        if (targetWorkgroup == null) {
            getLogger().error("Workgroup de destino não encontrado: " + TARGET_WORKGROUP_NAME);
            return;
        }

        // Iterar sobre os usuários e adicionar ao workgroup se o e-mail contiver 'nicolasec'
        for (Identity identity : identities) {
            String email = identity.getEmail();
            if (email != null && email.contains("@nicolasec")) {
                getLogger().info("Adicionando usuário ao workgroup: " + identity.getName());
                targetWorkgroup.add(identity);
                context.saveObject(targetWorkgroup);
            }
        }

        getLogger().info("Plugin EmailWorkgroupPlugin concluído.");
    }
}